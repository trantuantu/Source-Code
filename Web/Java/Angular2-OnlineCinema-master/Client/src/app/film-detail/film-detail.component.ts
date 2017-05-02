import {Component, OnInit} from '@angular/core';
import {FilmService} from "../_services/film.service";
import {ShowTime, TimesInDay} from "../_models/show-time";
import {ActivatedRoute} from "@angular/router";
import {Film} from "../_models/film";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.css'],
  providers: [FilmService]
})
export class FilmDetailComponent implements OnInit {

  // Params
  filmId: string;
  cinemaName: string = "none";
  title: string = "CHỌN RẠP PHIM";
  showtimes: ShowTime[] = [];
  trailerUrl: SafeResourceUrl;
  film: Film;
  isShow: boolean = false;

  constructor(private _filmService: FilmService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {

    this.film = new Film("", "", "", "", "", "", "");
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let paras = params['id'].split('_');
      this.filmId = paras[0];
      this.cinemaName = paras[1];
      this.isShow = false;
      if (this.cinemaName === "none") {
        this.cinemaName = 'Galaxy';
        this.isShow = true;
      }
      this.getShowTime(this.filmId, this.cinemaName);
      this.getFilmById();
    });
  }

  getShowTimeByCinema(cinemaIndex: number) {
    if (cinemaIndex === 1) { // Galaxy
      this.cinemaName = 'Galaxy';
      this.title = "RẠP GALAXY";
    }
    if (cinemaIndex === 2) { // cgv
      this.cinemaName = 'CGV';
      this.title = "RẠP CGV";
    }
    this.getShowTime(this.filmId, this.cinemaName);
  }

  getShowTime(filmId: string, cinemaName: string) {
    this._filmService.getShowTime(filmId, cinemaName)
      .subscribe(showTimes => {
          this.showtimes = [];
          for (let branchCinema in showTimes) {
            let days: TimesInDay[] = [];
            for (let day in showTimes[branchCinema]) {
              let hours: string[] = showTimes[branchCinema][day];
              let tmp = new TimesInDay(day, hours);
              days.push(tmp);
            }
            let showtime = new ShowTime(
              branchCinema,
              days
            );
            this.showtimes.push(showtime);
          }
        },
        err => {
          console.log(err);
        }
      );
  }

  getFilmById() {
    this._filmService.getFilmById(this.filmId).subscribe(
      film => {
        this.film = film;
        this.trailerUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.film.trailer);
      },
      err => {
        console.log(err);
      }
    );
  }
}
