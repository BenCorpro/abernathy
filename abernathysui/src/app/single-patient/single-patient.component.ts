import { Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Patient } from '../models/patient.model';
import { Assessment } from '../models/assessment.model'
import { AssessmentService } from '../services/assessment.service';
import { PatientService } from '../services/patient.service';

@Component({
  selector: 'app-single-patient',
  templateUrl: './single-patient.component.html',
  styleUrls: ['./single-patient.component.scss']
})
export class SinglePatientComponent implements OnInit {
  patient$!: Observable<Patient>;
  assessment$!: Observable<Assessment>;
  errorMessage!: string;

  constructor(private patientService: PatientService,
              private assessmentService: AssessmentService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    const patientId = +this.route.snapshot.params['id'];
    this.patient$ = this.patientService.getPatientById(patientId);
  }

  onPatientUpdate(patientId: number){
    this.router.navigateByUrl(`editpatient/${patientId}`);
  }

  onPatientDelete(patientId: number){
    let confirmation = confirm("Êtes vous sûr de vouloir supprimer le patient?");
    if(!confirmation) return;
    this.patientService.deletePatient(patientId).pipe(
      tap(() => this.router.navigateByUrl('/patients'))
    ).subscribe();
  }

  onNoteRequest(patientId: number){
    this.router.navigateByUrl(`listnotes/${patientId}`);
  }

  onAddNote(patientId: number){
    this.router.navigateByUrl(`newnote/${patientId}`);
  }

  onAssessRisk(patientId: number){
    this.assessment$ = this.assessmentService.getPatientRiskAssessmentById(patientId).pipe(
      tap(assess => console.log(assess.patientAge))
      ,catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    ); 
  }

  reloadPage(){
    this.ngOnInit();
  }

}
