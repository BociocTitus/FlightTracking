import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {SaveflightComponent} from './saveFlight/saveflight.component';

const routes: Routes = [
  {path: 'flight/:id', component: SaveflightComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
