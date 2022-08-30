import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { Note } from '../models/note.model';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.scss']
})
export class NoteListComponent implements OnInit {
  notes$!: Observable<Note[]>;
  errorMessage!: string;
  patientId!: number;

  constructor(private noteService: NoteService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.patientId = +this.route.snapshot.params['patientId']; 
    this.notes$ = this.noteService.getNotesByPatientId(this.patientId).pipe(
      catchError(err => {
        this.errorMessage = err.error.Error;
        return throwError(() => new Error(`${this.errorMessage}`));
      })
    );
  }

}
