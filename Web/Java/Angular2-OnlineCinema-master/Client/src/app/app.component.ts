import {Component, OnInit} from '@angular/core';
import {AngularFire} from "angularfire2";
import {MessagingService} from "./_services/messaging.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MessagingService]
})
export class AppComponent implements OnInit {

  constructor(private _msgService: MessagingService) {

  }

  ngOnInit(): void {
    this._msgService.getNewToken();
  }


}
