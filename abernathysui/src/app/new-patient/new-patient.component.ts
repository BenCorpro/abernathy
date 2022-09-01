import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { Patient } from '../models/patient.model';
import { PatientService } from '../services/patient.service';

@Component({
  selector: 'app-new-patient',
  templateUrl: './new-patient.component.html',
  styleUrls: ['./new-patient.component.scss']
})
export class NewPatientComponent implements OnInit {

  patientForm!: FormGroup;
  errorMessage!: string;
  phoneRegex!: RegExp;
  nameRegex!: RegExp;
  addressRegex!: RegExp;
  minDate!: Date;
  maxDate!: Date;

  constructor(private formBuilder: FormBuilder,
              private patientService: PatientService,
              private router: Router) {
                const currentYear = new Date().getFullYear(); 
                this.maxDate = new Date();
                this.minDate = new Date(currentYear - 120, 0, 1);
              }

  ngOnInit(): void {
    this.phoneRegex= /^[0-9-\\s]{3,12}$/;
    this.nameRegex= /^[-'a-zA-ZÀ-ÖØ-öø-ÿ\s]{3,15}$/;
    this.addressRegex= /^[-'a-zA-ZÀ-ÖØ-öø-ÿ0-9\s]{3,50}$/;
    this.patientForm = this.formBuilder.group({
      firstname: [null, [Validators.required, Validators.pattern(this.nameRegex)]],
      lastname: [null, [Validators.required, Validators.pattern(this.nameRegex)]],
      birthdate: [null, Validators.required],
      gender: [null, Validators.required],
      address: [null, Validators.pattern(this.addressRegex)],
      phone: [null, Validators.pattern(this.phoneRegex)]
    },{
      updateOn: 'blur'
    });
  }

  onSubmitForm(): void {
    this.patientService.addPatient(this.patientForm.value).pipe(
      tap(() => this.router.navigateByUrl('/patients')),
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    ).subscribe();
  }

}
