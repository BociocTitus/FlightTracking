export class Flight {
  id: number;
  arrivalAirport: string;
  departureAirport: string;
  departureTime: string;
  arrivalTime: string;
  flightCompany: string;

  constructor() {
    this.id = -1;
    this.arrivalAirport = '';
    this.departureAirport = '';
    this.departureTime = '';
    this.arrivalTime = '';
    this.flightCompany = '';
  }
}
