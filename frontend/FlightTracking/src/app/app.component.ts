import {Component, OnInit} from '@angular/core';
import {Flight} from './domain/Flight';
import {FlightService} from './service/FlightService';
import {Router} from '@angular/router';
import {FlightFilter} from './domain/FlightFilter';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  flights: Flight[];
  flightFilter: FlightFilter;
  categories: string[];
  flight: Flight = new Flight();
  isEdit: boolean;


  constructor(private flightService: FlightService, private router: Router) {
    this.flightService.getFlights().subscribe( flights =>
      this.flights = flights
    );
  }

  ngOnInit(): void {
    this.flightFilter = new FlightFilter();
    this.categories = ['RYANAIR', 'WIZZAIR', 'TAROM', 'BLUEAIR'];
  }

  save() {
    console.log(this.flight);
    this.flight.flightCompany = 'TAROM';
    if (this.isEdit) {
      this.flight.arrivalTime = this.flight.arrivalTime.replace('T', ' ');
      this.flight.departureTime = this.flight.departureTime.replace('T', ' ');
      this.flightService.updateFlight(this.flight).subscribe();
      location.reload();
    } else {
      this.flight.arrivalTime = this.flight.arrivalTime.replace('T', ' ');
      this.flight.departureTime = this.flight.departureTime.replace('T', ' ');
      this.flightService.addFlight(this.flight).subscribe();
      location.reload();
    }
  }

  create() {
    this.flight = new Flight();
    this.isEdit = false;
  }

  edit(id: number) {
    this.flightService.findById(id).subscribe(res => this.flight = res);
    this.isEdit = true;
  }

  delete(id: number) {
    console.log(id);
    this.flightService.deleteFlight(id).subscribe();
    location.reload();
  }

  search() {
    this.flightFilter.startTime = this.flightFilter.startTime.replace('T', ' ');
    this.flightFilter.endTime = this.flightFilter.endTime.replace('T', ' ');
    this.flightService.searchFlights(this.flightFilter).subscribe(res => this.flights = res);
  }
}
