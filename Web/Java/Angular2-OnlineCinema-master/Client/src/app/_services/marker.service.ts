/**
 * Created by Phuc-Hau Nguyen on 1/4/2017.
 */

import {Injectable} from '@angular/core'
import {MarkerStorage} from '../_models/maker-storage'

@Injectable()
export class MarkerService extends MarkerStorage {
  constructor() {
    super();
    this.load();
  }

  public getMarkers(cinema: string) {
    return JSON.parse(localStorage.getItem('markers_' + cinema));
  }
}

