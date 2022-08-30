import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Patient } from '../models/patient.model';
import { PatientService } from '../services/patient.service';

@Component({
  selector: 'app-patient-view',
  templateUrl: './patient-view.component.html',
  styleUrls: ['./patient-view.component.scss']
})
export class PatientViewComponent implements OnInit {
  @Input() patient!: Patient;

  constructor(private patientService: PatientService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onPatientAccess(){
    this.router.navigateByUrl(`patient/${this.patient.id}`);
  }
}
