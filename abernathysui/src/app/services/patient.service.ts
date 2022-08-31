import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Patient } from '../models/patient.model';
import { map, Observable, switchMap } from 'rxjs';
import { environment } from 'src/environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient){}

      getAllPatients(): Observable<Patient[]>{
        return this.http.get<Patient[]>(environment.patientHost+'/patients');
      }

      getPatientById(patientId: number): Observable<Patient> {
        return this.http.get<Patient>(environment.patientHost+`/patient/${patientId}`);
      }

      updatePatient(patientId: number, formValue: {firstname: string, lastname: string, birthdate: Date, gender: 'MALE' | 'FEMALE', address: string, phone: string }): Observable<Patient> {
        let modPatient: Patient;
        if(formValue.address == "" && formValue.phone == "") {
          modPatient = {...formValue, id: patientId, address: null, phone: null}
        } else if (formValue.phone == "") {
          modPatient = {...formValue, id: patientId, phone: null}
        } else if (formValue.address == "") {
          modPatient = {...formValue, id: patientId, address: null}
        } else {
          modPatient = {...formValue, id: patientId};
        };
        return this.http.put<Patient>(environment.patientHost+`/patient/update/${patientId}`, modPatient);
      }

      addPatient(formValue: {firstname: string, lastname: string, birthdate: Date, gender: 'MALE' | 'FEMALE', address: string, phone: string }): Observable<Patient> {
        let newPatient: Patient;
        if(formValue.address == "" && formValue.phone == "") {
          newPatient = {...formValue, id: 0, address: null, phone: null}
        } else if (formValue.phone == "") {
          newPatient = {...formValue, id: 0, phone: null}
        } else if (formValue.address == "") {
          newPatient = {...formValue, id: 0, address: null}
        } else {
          newPatient = {...formValue, id: 0};
        };
        return this.http.post<Patient>(environment.patientHost+`/patient/add`, newPatient);
        }

      searchPatient(formValue: {firstname: string, lastname: string}): Observable<Patient[]> {
        return this.http.get<Patient[]>(environment.patientHost+`/patient?given=${formValue.firstname}&family=${formValue.lastname}`)
      }

      deletePatient(patientId: number){
        return this.http.delete(environment.patientHost+`/patient/delete/${patientId}`);
      }
        
}