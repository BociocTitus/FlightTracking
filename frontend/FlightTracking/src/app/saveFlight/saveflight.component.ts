import {Component, OnInit} from '@angular/core';
import {Flight} from '../domain/Flight';
import {ActivatedRoute, Router} from '@angular/router';
import {FlightService} from '../service/FlightService';

@Component({
  selector: 'app-saveflight',
  templateUrl: './saveflight.component.html',
  styleUrls: ['./saveflight.component.css']
})
export class SaveflightComponent implements OnInit {

  flight: Flight = new Flight();
  categories = ['RYANAIR', 'WIZZAIR', 'TAROM', 'BLUEAIR'];
  isEdit: boolean;

  constructor(private route: ActivatedRoute, private flightService: FlightService, private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id'] !== '-1') {
        console.log(params['id'] !== -1);
        console.log(params['id']);
        this.flightService.findById(params['id']).subscribe(res => this.flight = res);
        console.log('edit');
        this.isEdit = true;
      } else {
        this.flight = new Flight();
      }
    });
  }

  save() {
    if (this.isEdit) {
      this.flightService.updateFlight(this.flight).subscribe();
    } else {
      this.flight.arrivalTime = this.flight.arrivalTime.replace('T', ' ');
      this.flight.departureTime = this.flight.departureTime.replace('T', ' ');
      this.flightService.addFlight(this.flight).subscribe();
    }
  }

}
