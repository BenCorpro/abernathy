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
  patientPreview$!: Observable<Patient>;
  errorMessage!: string;
  dateRegex!: RegExp;
  phoneRegex!: RegExp;
  nameRegex!: RegExp;
  addressRegex!: RegExp;

  constructor(private formBuilder: FormBuilder,
              private patientService: PatientService,
              private router: Router) { }

  ngOnInit(): void {
    this.dateRegex = /^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
    this.phoneRegex= /^[0-9-\\s]{3,12}$/;
    this.nameRegex= /^[a-zA-Z-\s]{3,15}$/;
    this.addressRegex= /^[a-zA-Z0-9-\s]{3,50}$/;
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

    this.patientPreview$ = this.patientForm.valueChanges.pipe(
      map(formValue => ({
        ...formValue,
        id: 0
      }))
    );
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
