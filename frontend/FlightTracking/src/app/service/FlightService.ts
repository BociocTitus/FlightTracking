import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Flight} from '../domain/Flight';
import {Observable} from 'rxjs';

export interface HttpOptions {
  headers;
}

@Injectable()
export class FlightService {

  private URL = 'http://localhost:8080/FlightTracking/rest/flights';

  constructor(private http: HttpClient) {
  }

  addFlight(flight: Flight): Observable<any> {
    console.log(JSON.stringify(flight));
    return this.http.post(this.URL, JSON.stringify(flight) , this.makeOptions());
  }

  getFlights(): Observable<any> {
    return this.http.get(this.URL, this.makeOptions());
  }

  findById(id: number): Observable<any> {
    return this.http.get(this.URL + '/flight/' + id, this.makeOptions());
  }

  updateFlight(flight: Flight) {
    return this.http.put(this.URL, this.makeOptions());
  }

  deleteFlight(id: number) {
    return this.http.delete(this.URL + '/' + id, this.makeOptions());
  }

  getHeaders(): HttpHeaders {
    let headers = new HttpHeaders();

    headers = headers.append('Accept', 'application/json');
    headers = headers.append('Content-Type', 'application/json');
    return headers;
  }

  makeOptions(): HttpOptions {
    return {headers: this.getHeaders()};
  }
}
