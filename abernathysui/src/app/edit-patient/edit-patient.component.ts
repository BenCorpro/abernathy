import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Patient } from '../models/patient.model';
import { PatientService } from '../services/patient.service';
import { catchError, map, Observable, tap, throwError } from 'rxjs';

@Component({
  selector: 'app-edit-patient',
  templateUrl: './edit-patient.component.html',
  styleUrls: ['./edit-patient.component.scss']
})
export class EditPatientComponent implements OnInit {

  patientId!: number;
  patient!: Patient;
  patientForm!: FormGroup;
  errorMessage!: string;
  dateRegex!: RegExp;
  phoneRegex!: RegExp;
  nameRegex!: RegExp;
  addressRegex!: RegExp;

  constructor(private route: ActivatedRoute,
              private patientService: PatientService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.patientId = this.route.snapshot.params['id'];
   }

  ngOnInit(): void {
    this.dateRegex = /^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
    this.phoneRegex= /^[0-9-\\s]{3,12}$/;
    this.nameRegex= /^[a-zA-Z-\s]{3,15}$/;
    this.addressRegex= /^[a-zA-Z0-9-\s]{3,50}$/;
    this.patientService.getPatientById(this.patientId).subscribe({
      next:(patient: Patient) => {
        this.patient = patient;
        this.patientForm = this.formBuilder.group({
          firstname: [this.patient.firstname, [Validators.required, Validators.pattern(this.nameRegex)]],
          lastname: [this.patient.lastname, [Validators.required, Validators.pattern(this.nameRegex)]],
          birthdate: [this.patient.birthdate, Validators.required],
          gender: [this.patient.gender, Validators.required],
          address: [this.patient.address, Validators.pattern(this.addressRegex)],
          phone: [this.patient.phone, Validators.pattern(this.phoneRegex)]
        },{
          updateOn: 'blur'
        });
      },
      error: (err) =>{
        console.log(err);
      }
    });
  }

  onUpdateForm(): void {
    this.patientService.updatePatient(this.patientId, this.patientForm.value).pipe(
      tap(() => this.router.navigateByUrl(`/patient/${this.patientId}`)),
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    ).subscribe();
  }

}
