import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PersonService } from './services/person.service';
import { Person } from './models/person';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  persons: Person[] = [];
  newPerson: Person = {
    vorname: '',
    nachname: '',
    birthDate: '',
    verheiratet: false
  };

  constructor(private personService: PersonService) { }

  ngOnInit() {
    this.loadPersons();
  }

  loadPersons() {
    this.personService.getPersons().subscribe(data => {
      this.persons = data;
    });
  }

  onSubmit() {
    this.personService.savePerson(this.newPerson).subscribe(() => {
      this.loadPersons();
      this.newPerson = {
        vorname: '',
        nachname: '',
        birthDate: '',
        verheiratet: false
      };
    });
  }
}
