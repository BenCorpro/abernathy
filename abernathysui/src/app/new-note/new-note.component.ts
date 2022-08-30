import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { Note } from '../models/note.model';
import { Patient } from '../models/patient.model';
import { NoteService } from '../services/note.service';
import { PatientService } from '../services/patient.service';

@Component({
  selector: 'app-new-note',
  templateUrl: './new-note.component.html',
  styleUrls: ['./new-note.component.scss']
})
export class NewNoteComponent implements OnInit {

  patientId!: number;
  patient!: Patient;
  noteForm!: FormGroup;
  notePreview$!: Observable<Note>;
  errorMessage!: string;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private noteService: NoteService,
              private patientService: PatientService,
              private router: Router) { 
                this.patientId = this.route.snapshot.params['patientId'];
              }

  ngOnInit(): void {
    this.patientService.getPatientById(this.patientId).subscribe({
      next:(patient: Patient) => {
        this.patient = patient;
        this.noteForm = this.formBuilder.group({
          patientId: [this.patientId, Validators.required],
          patientName: [this.patient.lastname, Validators.required],
          recommendation: [null, Validators.required]
        }, {
          updateOn: 'blur'
        })
      }
    }); 

    this.notePreview$ = this.noteForm.valueChanges.pipe(
      map(formValue => ({
        ...formValue,
        id: 0
      }))
    );
  }

  onSubmitNoteForm(): void {
    this.noteService.addNote(this.noteForm.value).pipe(
      tap(() => this.router.navigateByUrl(`listnotes/${this.patientId}`)),
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    ).subscribe();
  }

}
