import {Component, OnInit} from '@angular/core';
import {DomSanitizer, SafeResourceUrl, SafeUrl} from "@angular/platform-browser";
import {FilmService} from "../_services/film.service";
import {Film} from "../_models/film";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-film-gallery',
  templateUrl: './film-gallery.component.html',
  styleUrls: ['./film-gallery.component.css'],
  providers: [FilmService]
})
export class FilmGalleryComponent implements OnInit {

  cinema: string;

  title: string;
  url: string;
  films: Film[];
  trailerUrl: SafeResourceUrl;

  constructor(private _filmService: FilmService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {
    this.cinema = "CGV";
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.cinema = params['id'];
      if (this.cinema === 'CGV') {
        this.title = 'RẠP CGV CINEMA';
        this.url = "https://www.cgv.vn/vn/movies/now-showing.html";
      }
      if (this.cinema === 'Galaxy') {
        this.title = 'RẠP GALAXY CINEMA';
        this.url = "https://www.galaxycine.vn/rap-gia-ve";
      }
      if (this.cinema === 'BHD') {
        this.title = 'RẠP BHD CINEMA';
        this.url = "http://bhdstar.vn/";
      }
      if (this.cinema === 'Lotte') {
        this.title = 'RẠP LOTTE CINEMA';
        this.url = "http://lottecinemavn.com/";
      }
      this.getFilms();
    });
  }

  getFilms() {
    this._filmService.getFilmsByCinema(this.cinema)
      .subscribe(films => {
          this.films = films
        },
        err => {
          console.log(err);
        }
      );
  }

  getTrailer(trailer: string) {
    this.trailerUrl = this.sanitizer.bypassSecurityTrustResourceUrl(trailer);
  }
}
