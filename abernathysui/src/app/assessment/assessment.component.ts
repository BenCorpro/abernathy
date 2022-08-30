import { Component, OnInit, Input } from '@angular/core';
import { Assessment } from '../models/assessment.model';

@Component({
  selector: 'app-assessment',
  templateUrl: './assessment.component.html',
  styleUrls: ['./assessment.component.scss']
})
export class AssessmentComponent implements OnInit {
  @Input() assessment!: Assessment;
  
  constructor() { }

  ngOnInit(): void {
  }

}
