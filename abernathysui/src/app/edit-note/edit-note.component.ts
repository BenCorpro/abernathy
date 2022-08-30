import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Note } from '../models/note.model';
import { NoteService } from '../services/note.service';
import { catchError, map, Observable, tap, throwError } from 'rxjs';

@Component({
  selector: 'app-edit-note',
  templateUrl: './edit-note.component.html',
  styleUrls: ['./edit-note.component.scss']
})
export class EditNoteComponent implements OnInit {

  noteId!: string;
  note!: Note;
  noteForm!: FormGroup;
  notePreview$!: Observable<Note>;
  errorMessage!: string;

  constructor(private route: ActivatedRoute,
              private noteService: NoteService,
              private formBuilder: FormBuilder,
              private router: Router) {
                this.noteId = this.route.snapshot.params['id'];
               }

  ngOnInit(): void {
    this.noteService.getNoteById(this.noteId).subscribe({  
      next:(note: Note) => {
        this.note = note;
        this.noteForm = this.formBuilder.group({
          createdAt: [this.note.createdAt],
          patientId: [this.note.patientId, Validators.required],
          patientName: [this.note.patientName, Validators.required],
          recommendation: [this.note.recommendation, Validators.required]
        },{
          updateOn: 'blur'
        });
      },
      error: (err) =>{
        console.log(err);
      }
    });
  }

  onUpdateNoteForm(): void {
    this.noteService.updateNote(this.noteId, this.noteForm.value).pipe(
      tap(() => this.router.navigateByUrl(`listnotes/${this.note.patientId}`)),
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    ).subscribe();
  }

}
