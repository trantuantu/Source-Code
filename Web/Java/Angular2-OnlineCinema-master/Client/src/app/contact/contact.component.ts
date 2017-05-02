import {Component, OnInit} from '@angular/core';
import {MessagingService} from "../_services/messaging.service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  constructor(private _msgService: MessagingService) {
  }

  ngOnInit() {
  }

  sendMessage() {
    this._msgService.sendMessage();
  }
}
