/**
 * Created by Phuc-Hau Nguyen on 12/28/2016.
 */
import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {ContactComponent} from "./contact/contact.component";
import {LoginComponent} from "./login/login.component";
import {FilmComponent} from "./film/film.component";
import {MapComponent} from "./map/map.component";
import {HomeComponent} from "./home/home.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AboutComponent} from "./about/about.component";
import {FilmDetailComponent} from "./film-detail/film-detail.component";
import {FilmGalleryComponent} from "./film-gallery/film-gallery.component";

export const routes: Routes = [
  {path: '', component: FilmComponent},
  {path: '', redirectTo: '/film', pathMatch: 'full'},
  {path: '#', redirectTo: '/**', pathMatch: 'full'},
  {path: '#', component: FilmComponent},
  {path: 'hot', component: HomeComponent},
  {path: 'home', component: FilmComponent},
  {path: 'film', component: FilmComponent},
  {path: 'film/:id', component: FilmDetailComponent},
  {
    path: 'film/:id', component: FilmDetailComponent,
    children: [
      {path: 'galaxy', component: FilmDetailComponent},
      {path: 'cgv', component: FilmDetailComponent}
    ]
  },
  {path: 'login', component: LoginComponent},
  {path: 'cinema/:id', component: FilmGalleryComponent},
  {path: 'map', component: MapComponent},
  {path: 'about', component: AboutComponent},
  {path: 'contact', component: ContactComponent},
  {path: '**', component: PageNotFoundComponent}

];
export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
