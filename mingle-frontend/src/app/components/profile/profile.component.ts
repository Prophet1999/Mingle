import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container mx-auto p-4">
      <h1 class="text-3xl font-bold mb-6">My Profile</h1>
      <div *ngIf="user" class="bg-white rounded shadow p-6 max-w-2xl mx-auto">
        <div class="mb-4 text-center">
          <img [src]="user.imageUrl || 'https://via.placeholder.com/150'" alt="Profile" class="w-32 h-32 rounded-full object-cover mx-auto mb-4">
          <div class="mb-2">
             <label class="block text-gray-700 text-sm font-bold mb-1">Profile Photo URL</label>
             <input [(ngModel)]="user.imageUrl" class="w-full border rounded px-3 py-2 text-sm">
          </div>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="mb-4">
              <label class="block text-gray-700 font-bold mb-2">Name</label>
              <input [(ngModel)]="user.name" class="w-full border rounded px-3 py-2">
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 font-bold mb-2">Date of Birth</label>
              <input type="date" [(ngModel)]="user.dob" class="w-full border rounded px-3 py-2">
            </div>
        </div>

        <div class="mb-4">
          <label class="block text-gray-700 font-bold mb-2">Bio</label>
          <textarea [(ngModel)]="user.bio" class="w-full border rounded px-3 py-2" rows="3"></textarea>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="mb-4">
              <label class="block text-gray-700 font-bold mb-2">Gender</label>
              <select [(ngModel)]="user.gender" class="w-full border rounded px-3 py-2">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>
            </div>
            <div class="mb-6">
              <label class="block text-gray-700 font-bold mb-2">Interested In</label>
              <select [(ngModel)]="user.interestedIn" class="w-full border rounded px-3 py-2">
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="BOTH">Both</option>
              </select>
            </div>
        </div>

        <hr class="my-6">
        <h2 class="text-xl font-bold mb-4">Preferences</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div class="mb-4">
              <label class="block text-gray-700 font-bold mb-2">Min Age</label>
              <input type="number" [(ngModel)]="user.minAgePreference" class="w-full border rounded px-3 py-2">
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 font-bold mb-2">Max Age</label>
              <input type="number" [(ngModel)]="user.maxAgePreference" class="w-full border rounded px-3 py-2">
            </div>
            <div class="mb-4">
              <label class="block text-gray-700 font-bold mb-2">Max Distance (Km)</label>
              <input type="number" [(ngModel)]="user.maxDistancePreference" class="w-full border rounded px-3 py-2">
            </div>
        </div>

        <button (click)="saveProfile()" class="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600 w-full mt-4">Save Profile</button>
      </div>
    </div>
  `
})
export class ProfileComponent implements OnInit {
  user: any;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe({
      next: (data) => this.user = data,
      error: (err) => console.error(err)
    });
  }

  saveProfile() {
    this.userService.updateProfile(this.user).subscribe({
      next: (updatedUser) => {
        this.user = updatedUser;
        alert('Profile updated successfully!');
      },
      error: (err) => console.error(err)
    });
  }
}
