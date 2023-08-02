import { Component } from '@angular/core';

@Component({
  selector: 'app-affectation-conge',
  templateUrl: './affectation-conge.component.html'
})
export class AffectationCongeComponent {
  filteredCountries: any[] = [];
  countries: any[] = [];

  searchEmployee(event: any) {
    // in a real application, make a request to a remote url with the query and
    // return filtered results, for demo we filter at client side
    const filtered: any[] = [];
    const query = event.query;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.countries.length; i++) {
        const country = this.countries[i];
        if (country.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(country);
        }
    }

    this.filteredCountries = filtered;
}
}
