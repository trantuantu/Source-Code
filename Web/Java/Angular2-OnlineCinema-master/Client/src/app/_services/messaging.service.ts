import {Injectable, Inject} from '@angular/core';
import * as firebase from 'firebase';
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import {Global} from "../_global/global";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {FirebaseApp} from "angularfire2";

@Injectable()
export class MessagingService {

  private currentToken: string = "";
  private _messaging: firebase.messaging.Messaging;

  constructor(private http: Http,
              @Inject(FirebaseApp)
              private _firebaseApp: firebase.app.App) {

    this.currentToken = JSON.parse(localStorage.getItem('firebase_token'));
    this._messaging = firebase.messaging(this._firebaseApp);
  }

  getNewToken() {
    this.requestPermission();
  }

  private requestPermission() {
    this._messaging.requestPermission()
      .then(() => {
        this.getToken();
      })
      .catch(err => {
        console.log('Unable to get permission to notify.', err);
      });
  }

  private getToken() {
    this._messaging.getToken().then(token => {
      if (this.currentToken === token) {
        this._messaging.deleteToken(token).then(() => {
          this._messaging.getToken().then(
            refreshedToken => {
              this.currentToken = refreshedToken;
              this.saveToken(this.currentToken);
              this.sendTokenToServer(this.currentToken).then(
                token => {
                  console.log("res:" + token)
                },
                error => {
                  console.log(error)
                });
            }
          ).catch(err => {
            console.error('Msg Token error:', err);
          });
        });
      }
      else {
        this.currentToken = token;
        this.saveToken(this.currentToken);
        this.sendTokenToServer(this.currentToken).then(
          token => {
            console.log("res:" + token)
          },
          error => {
            console.log(error)
          });
      }
    });
  }

  private saveToken(token: string) {
    this.currentToken = token;
    localStorage.setItem('firebase_token', JSON.stringify(this.currentToken));
  }

  private sendTokenToServer(token: string): Promise<string> {
    let headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    let options = new RequestOptions({headers: headers});
    let body = 'token=' + token;
    return this.http.post(Global.API_REGISTER_NOTI, body, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    return res || {};
  }

  private handleError(error: Response | any) {
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    return Promise.reject(errMsg);
  }

  sendMessage() {
    let headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    let options = new RequestOptions({headers: headers});
    let title = 'Online Cinema';
    let content = 'We will reply as soon as possible!';
    let body = 'token=&title=' + title + '&content=' + content + '&link=https://github.com/nphau';
    return this.http.post(Global.API_REGISTER_PUT_NOTI, body, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }
}
