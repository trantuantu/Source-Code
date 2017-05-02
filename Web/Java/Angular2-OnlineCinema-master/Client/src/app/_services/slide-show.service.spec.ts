/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { SlideShowService } from './slide-show.service';

describe('SlideShowService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SlideShowService]
    });
  });

  it('should ...', inject([SlideShowService], (service: SlideShowService) => {
    expect(service).toBeTruthy();
  }));
});
