import { CaloryPalPage } from './app.po';

describe('Calory Count App', () => {
  let page: CaloryPalPage;

  beforeEach(() => {
    page = new CaloryPalPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
