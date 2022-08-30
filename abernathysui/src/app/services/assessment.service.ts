import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Assessment } from '../models/assessment.model';
import { environment } from 'src/environments/environment'; 

@Injectable({
    providedIn: 'root'
  })
export class AssessmentService{

    constructor(private http: HttpClient){}

    getPatientRiskAssessmentById(patientId: number): Observable<Assessment>{
      return this.http.get<Assessment>(environment.reportHost+`/report/${patientId}`)
    }
    
    getPatientRiskAssessmentByName(lastname: string): Observable<Assessment> {
      return this.http.get<Assessment>(environment.reportHost+`/report/${lastname}`)
    }
}