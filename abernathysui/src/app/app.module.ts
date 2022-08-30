import { NgModule, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import  { registerLocaleData} from '@angular/common';
import  { HttpClientModule} from '@angular/common/http';
import * as fr from '@angular/common/locales/fr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientViewComponent } from './patient-view/patient-view.component';
import { PatientListComponent } from './patient-list/patient-list.component';
import { HeaderComponent } from './header/header.component';
import { HomePageComponent } from './home-page/home-page.component';
import { SinglePatientComponent } from './single-patient/single-patient.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NewPatientComponent } from './new-patient/new-patient.component';
import { EditPatientComponent } from './edit-patient/edit-patient.component';
import { NoteListComponent } from './note-list/note-list.component';
import { NoteComponent } from './note/note.component';
import { EditNoteComponent } from './edit-note/edit-note.component';
import { NewNoteComponent } from './new-note/new-note.component';
import { AssessmentComponent } from './assessment/assessment.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatDividerModule} from '@angular/material/divider';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';

@NgModule({
  declarations: [
    AppComponent,
    PatientViewComponent,
    PatientListComponent,
    HeaderComponent,
    HomePageComponent,
    SinglePatientComponent,
    NewPatientComponent,
    EditPatientComponent,
    NoteListComponent,
    NoteComponent,
    EditNoteComponent,
    NewNoteComponent,
    AssessmentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatDividerModule,
    MatInputModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatButtonToggleModule,
    MatCardModule,
    MatNativeDateModule,
    MatListModule,
    MatMenuModule,
    MatIconModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(){
    registerLocaleData(fr.default);
  }
}
