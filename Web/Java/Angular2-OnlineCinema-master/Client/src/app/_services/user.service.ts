import {Injectable} from '@angular/core';
import {Http, Response, RequestOptions, Headers} from "@angular/http";
import {Observable} from "rxjs";
import {Global} from "../_global/global";

@Injectable()
export class UserService {

  constructor(private http: Http) {

  }

  login(id: string): Observable<string> {
    let headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'}); // ... Set content type to JSON
    let options = new RequestOptions({headers: headers}); // Create a request option

    return this.http.post(Global.API_LOGIN, id, options) // ...using post request
      .map((res: Response) => res) // ...and calling .json() on the response to return data
      .catch((error: any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
  }
}
