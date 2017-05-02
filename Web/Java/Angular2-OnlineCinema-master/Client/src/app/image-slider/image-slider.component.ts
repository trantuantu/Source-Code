import {Component, OnInit, Input} from '@angular/core';
import {SlideShowService} from "../_services/slide-show.service";
import {SlideShow} from "../_models/slide-show";

@Component({
  selector: 'app-image-slider',
  templateUrl: './image-slider.component.html',
  styleUrls: ['./image-slider.component.css'],
  providers: [SlideShowService]
})
export class ImageSliderComponent implements OnInit {

  @Input() cinema: string = 'Galaxy';

  slideShows: SlideShow[];
  styleWidth: string;

  constructor(private _slideShowService: SlideShowService) {

  }

  ngOnInit() {
    this.loadSlideShow(this.cinema);
  }

  loadSlideShow(cinema: string) {
    this._slideShowService.getSlideByCinema(cinema)
      .subscribe(slideShows => {
          this.slideShows = slideShows;
          this.styleWidth = ((this.slideShows.length + 1) * 100) + "%";
        },
        err => {
          console.log(err);
        }
      );
  }
}
