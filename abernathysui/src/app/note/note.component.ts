import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { Note } from '../models/note.model';
import { NoteService } from '../services/note.service';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {
  @Input() note!: Note;

  constructor(private noteService: NoteService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onNoteUpdate(noteId: string | null){
    this.router.navigateByUrl(`editnote/${noteId}`);
  }

  onNoteDelete(noteId: string | null){
    let confirmation = confirm("Êtes vous sûr de vouloir supprimer la note?");
    if(!confirmation) return;
    this.noteService.deleteNote(noteId).pipe(
      tap(() => this.router.navigateByUrl(`patient/${this.note.patientId}`)) 
    ).subscribe();
  }

}
