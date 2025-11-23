import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatchingService } from '../../services/matching.service';

@Component({
    selector: 'app-matches',
    standalone: true,
    imports: [CommonModule],
    template: `
    <div class="container mx-auto p-4">
      <h1 class="text-3xl font-bold mb-4">Your Matches</h1>
      <div *ngIf="matches.length === 0" class="text-center text-gray-500">
        No matches yet. Keep swiping!
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div *ngFor="let match of matches" class="bg-white rounded shadow p-4 cursor-pointer hover:bg-gray-50" (click)="openChat(match)">
          <div class="flex items-center">
            <img [src]="match.imageUrl || 'https://via.placeholder.com/50'" alt="Profile" class="w-12 h-12 rounded-full object-cover mr-4">
            <div>
              <h2 class="text-xl font-bold">{{ match.name }}</h2>
              <p class="text-gray-600 text-sm">Click to chat</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class MatchesComponent implements OnInit {
    matches: any[] = [];

    constructor(private matchingService: MatchingService, private router: Router) { }

    ngOnInit() {
        this.loadMatches();
    }

    loadMatches() {
        this.matchingService.getMatches().subscribe({
            next: (data) => this.matches = data,
            error: (err) => console.error(err)
        });
    }

    openChat(match: any) {
        this.router.navigate(['/chat', match.id]);
    }
}
