import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchingService } from '../../services/matching.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mx-auto p-4">
      <h1 class="text-3xl font-bold mb-4">Find Your Match</h1>
      <div *ngIf="candidates.length === 0" class="text-center text-gray-500">
        No more candidates nearby. Try updating your location or come back later.
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div *ngFor="let candidate of candidates" class="bg-white rounded shadow p-4">
          <img [src]="candidate.imageUrl || 'https://via.placeholder.com/150'" alt="Profile" class="w-full h-48 object-cover rounded mb-4">
          <h2 class="text-xl font-bold">{{ candidate.name }} <span *ngIf="candidate.age">, {{ candidate.age }}</span></h2>
          <p class="text-gray-600">{{ candidate.bio || 'No bio available' }}</p>
          <div class="mt-4 flex justify-between">
            <button (click)="pass(candidate)" class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">Pass</button>
            <button (click)="like(candidate)" class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">Like</button>
          </div>
        </div>
      </div>
    </div>
  `
})
export class HomeComponent implements OnInit {
  candidates: any[] = [];

  constructor(private matchingService: MatchingService) { }

  ngOnInit() {
    this.loadCandidates();
  }

  loadCandidates() {
    this.matchingService.getCandidates().subscribe({
      next: (data) => {
        this.candidates = data.map(user => ({
          ...user,
          age: this.calculateAge(user.dob)
        }));
      },
      error: (err) => console.error(err)
    });
  }

  calculateAge(dob: string): number | null {
    if (!dob) return null;
    const birthDate = new Date(dob);
    const ageDifMs = Date.now() - birthDate.getTime();
    const ageDate = new Date(ageDifMs);
    return Math.abs(ageDate.getUTCFullYear() - 1970);
  }

  like(candidate: any) {
    this.matchingService.likeUser(candidate.id).subscribe({
      next: (response) => {
        if (response.match) {
          alert(`It's a match with ${candidate.name}!`);
        }
        this.removeCandidate(candidate);
      },
      error: (err) => console.error(err)
    });
  }

  pass(candidate: any) {
    this.matchingService.dislikeUser(candidate.id).subscribe({
      next: () => {
        this.removeCandidate(candidate);
      },
      error: (err) => console.error(err)
    });
  }

  removeCandidate(candidate: any) {
    this.candidates = this.candidates.filter(c => c.id !== candidate.id);
  }
}
