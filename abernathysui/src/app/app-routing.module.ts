import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditNoteComponent } from './edit-note/edit-note.component';
import { EditPatientComponent } from './edit-patient/edit-patient.component';
import { HomePageComponent } from './home-page/home-page.component';
import { NewNoteComponent } from './new-note/new-note.component';
import { NewPatientComponent } from './new-patient/new-patient.component';
import { NoteListComponent } from './note-list/note-list.component';
import { PatientListComponent } from './patient-list/patient-list.component';
import { SinglePatientComponent } from './single-patient/single-patient.component';

const routes: Routes = [
  { path: 'patient/:id', component: SinglePatientComponent },
  { path: 'newpatient', component: NewPatientComponent },
  { path: 'editpatient/:id', component: EditPatientComponent },
  { path: 'patients', component: PatientListComponent },
  { path: 'listnotes/:patientId', component: NoteListComponent },
  { path: 'editnote/:id', component: EditNoteComponent },
  { path: 'newnote/:patientId', component: NewNoteComponent },
  { path: '', component: HomePageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
