import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GomokuComponent } from './gomoku.component';

describe('GomokuComponent', () => {
  let component: GomokuComponent;
  let fixture: ComponentFixture<GomokuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GomokuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GomokuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
