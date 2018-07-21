import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveflightComponent } from './saveflight.component';

describe('SaveflightComponent', () => {
  let component: SaveflightComponent;
  let fixture: ComponentFixture<SaveflightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveflightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveflightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
