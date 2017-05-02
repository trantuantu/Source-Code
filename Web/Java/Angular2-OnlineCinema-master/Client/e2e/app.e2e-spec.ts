import { OnlineCinemaPage } from './app.po';

describe('online-cinema App', function() {
  let page: OnlineCinemaPage;

  beforeEach(() => {
    page = new OnlineCinemaPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
