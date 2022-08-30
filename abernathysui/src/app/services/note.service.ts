import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Note } from '../models/note.model';
import { map, Observable, switchMap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
  })
export class NoteService {

    constructor(private http: HttpClient){}

      getAllNotes(): Observable<Note[]>{
        return this.http.get<Note[]>(environment.noteHost+'/notes');
      }

      getNoteById(noteId: string): Observable<Note> {
        return this.http.get<Note>(environment.noteHost+`/note/${noteId}`);
      }

      updateNote(noteId: string, formValue: {createdAt: Date, patientId: number, patientName: string, recommendation: string}): Observable<Note> {
        let modNote: Note = {...formValue, id: noteId, updatedAt: null};
        return this.http.put<Note>(environment.noteHost+`/note/update/${noteId}`, modNote);
      }

      addNote(formValue: {patientId: number, patientName: string, recommendation: string}): Observable<Note> {
        let newNote: Note = {...formValue, id: null, createdAt: null, updatedAt: null};  
        return this.http.post<Note>(environment.noteHost+`/note/add`, newNote);
        }

      getNotesByPatientId(patientId: number): Observable<Note[]> {
        return this.http.get<Note[]>(environment.noteHost+`/notes/${patientId}`)
      }

      deleteNote(noteId: string | null){
        return this.http.delete(environment.noteHost+`/note/delete/${noteId}`);
      }

}