import {Injectable} from '@angular/core';
import {Film} from "../_models/film";
import {Observable} from "rxjs";
import {Http} from "@angular/http";
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Global} from "../_global/global";
import {DomSanitizer} from "@angular/platform-browser";

@Injectable()
export class FilmService {

  constructor(private http: Http,
              private sanitizer: DomSanitizer) {

  }

  getFilmsShowing(): Observable<Film[]> {
    return this.http.get(Global.API_FILMS_SHOWING)
      .map(response => response.json().films)
      .catch(error => Observable.throw(error.json().error || 'Server error'));
  }

  getFilmsCommingSoon(): Observable<Film[]> {
    return this.http.get(Global.API_FILMS_COMINGSOON)
      .map(response => response.json().films)
      .catch(error => Observable.throw(error.json().error || 'Server error'));
  }

  getFilmsByCinema(cinema: string): Observable<Film[]> {
    return this.http.get(Global.API_FILMS_BY_CINEMA + cinema)
      .map(response => response.json().films)
      .catch(error => Observable.throw(error.json().error || 'Server error'));
  }

  getFilmById(filmId: string): Observable<Film> {
    return this.http.get(Global.API_FILMS + filmId)
      .map(res => res.json())
      .catch(error => Observable.throw(error.json().error || 'Server error'));
  }

  getShowTime(filmId: string, cinemaName: string) {
    return this.http.get(Global.API_FILMS + filmId + "/" + cinemaName)
      .map(res => res.json().showtimes)
      .catch(error => Observable.throw(error.json().error || 'Server error'));
  }
}
