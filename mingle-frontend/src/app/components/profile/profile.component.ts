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
          <button class="text-blue-500 text-sm">Change Photo</button>
        </div>
        <div class="mb-4">
          <label class="block text-gray-700 font-bold mb-2">Name</label>
          <input [(ngModel)]="user.name" class="w-full border rounded px-3 py-2">
        </div>
        <div class="mb-4">
          <label class="block text-gray-700 font-bold mb-2">Bio</label>
          <textarea [(ngModel)]="user.bio" class="w-full border rounded px-3 py-2" rows="3"></textarea>
        </div>
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
        <button (click)="saveProfile()" class="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600 w-full">Save Profile</button>
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
