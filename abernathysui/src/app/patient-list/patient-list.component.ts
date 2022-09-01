import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Patient } from '../models/patient.model';
import { PatientService } from '../services/patient.service';
import { BreakpointObserver, BreakpointState } from '@angular/cdk/layout';
import { Router } from '@angular/router';

const SMALL_WIDTH_BREAKPOINT = 720;

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {
  patients$!: Observable<Patient[]>;
  errorMessage!: string;
  searchForm!: FormGroup;
  isScreenSmall!: boolean;

  constructor(private patientService: PatientService,
              private formBuilder: FormBuilder,
              private breakpointObserver: BreakpointObserver,
              private router: Router) { }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      firstname: [''],
      lastname: ['']
    });

    this.patients$ = this.patientService.getAllPatients().pipe(
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    );

    this.breakpointObserver.observe([`(max-width:${SMALL_WIDTH_BREAKPOINT}px)`])
    .subscribe((state: BreakpointState) =>
    {
      this.isScreenSmall = state.matches;
    });
  }

  onSearchForm(){
    this.patients$ = this.patientService.searchPatient(this.searchForm.value).pipe(
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    );
  }

  onPatientAccess(id: number){
    this.router.navigateByUrl(`patient/${id}`);
  }

  reloadPage(){
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
  }
}
