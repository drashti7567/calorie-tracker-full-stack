import { CaloriePalPage } from './app.po';

describe('Calorie Count App', () => {
  let page: CaloriePalPage;

  beforeEach(() => {
    page = new CaloriePalPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
