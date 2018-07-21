import {Component, OnInit} from '@angular/core';
import {Flight} from './domain/Flight';
import {FlightService} from './service/FlightService';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  flights: Flight[];

  constructor(private flightService: FlightService, private router: Router) {
    this.flightService.getFlights().subscribe( flights =>
      this.flights = flights
    );
  }

  ngOnInit(): void {
  }

  create() {
    this.router.navigate(['flight', -1]);
  }

  edit(id: number) {
    this.router.navigate(['flight', id]);
  }
}
