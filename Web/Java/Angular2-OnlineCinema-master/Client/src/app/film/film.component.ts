import {Component, OnInit} from '@angular/core';
import {Film} from "../_models/film";
import {FilmService} from "../_services/film.service";
import {TabMenu} from "../_interfaces/tab-menu.interface";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {
  FacebookService, FacebookInitParams,
  FacebookUiParams
} from 'ng2-facebook-sdk';
@Component({
  selector: 'app-film',
  templateUrl: './film.component.html',
  styleUrls: ['./film.component.css'],
  providers: [FilmService, FacebookService]
})
export class FilmComponent implements OnInit {

  films: Film[];
  trailerUrl: SafeResourceUrl;

  constructor(private fb: FacebookService,
              private _filmService: FilmService,
              private sanitizer: DomSanitizer) {

  }

  ngOnInit() {
    this.getFilmsShowing();
    this.initFB();
  }

  getFilmsShowing() {
    this._filmService.getFilmsShowing()
      .subscribe(films => {
          this.films = films
        },
        err => {
          console.log(err);
        }
      );
  }

  getFilmsCommingSoon() {
    this._filmService.getFilmsCommingSoon()
      .subscribe(films => {
          this.films = films
        },
        err => {
          console.log(err);
        }
      );
  }

  tabMenus: TabMenu[] = [
    {
      title: "Phim đang chiếu",
      active: true
    },
    {
      title: "Phim sắp chiếu",
      active: false
    }
  ];

  clicked(index) {
    for (let i in this.tabMenus) {
      this.tabMenus[i].active = false;
    }
    this.tabMenus[index].active = true;
    if (index === 0) {
      this.getFilmsShowing();
    }
    if (index === 1) {
      this.getFilmsCommingSoon();
    }
  }

  private film: Film;

  getTrailer(film: Film) {
    this.film = film;
    this.trailerUrl = this.sanitizer.bypassSecurityTrustResourceUrl(film.trailer);
  }

  initFB(): void {
    let fbParams: FacebookInitParams = {
      appId: '1650129628613117',
      xfbml: true,
      status: true,
      cookie: true,
      version: 'v2.4'
    };
    this.fb.init(fbParams);
  }

  share(film: Film) {
    this.film = film;
    let fbUiParams: FacebookUiParams = {
      method: 'share',
      href: 'https:' + this.film.trailer,
      mobile_iframe: 'true',
      caption: 'Hãy xem phim này cùng tôi bạn nhé ... ',
      description: this.film.name,
      picture: this.film.img
    };
    this.fb.ui(fbUiParams);
  }
}
