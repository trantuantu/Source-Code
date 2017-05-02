export class TimesInDay {
  day: string;
  times: string[];

  constructor(day: string, times: string[]) {
    this.day = day;
    this.times = times;
  }
}
export class ShowTime {
  name: string; // Cinema name
  times: TimesInDay[]; // List of days
  constructor(name: string, times: TimesInDay[]) {
    this.name = name;
    this.times = times;
  }
}
