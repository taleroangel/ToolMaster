import { Component, OnInit } from '@angular/core';
import { Pageable } from 'src/app/interfaces/pageable';
import { User } from 'src/app/models/user';
import { UserService, UserSort } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  public US = UserSort;

  public isReady = false;
  public pagination!: Pageable<User>;
  public users!: User[];

  public currentPage: number = 0;
  public currentSort: UserSort = UserSort.NONE;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.fetchContent()
  }

  checkAuthenticated(): boolean {
    return this.userService.authService.isAuthenticated()
  }

  fetchContent(): void {
    this.userService.searchAllUsers(this.currentSort, this.currentPage).subscribe({
      next: data => {
        this.pagination = data;
        this.users = data.content;
        this.isReady = true;
      },
      error: error => {
        console.error(error);
        alert("Error!, No se ha podido conectar con el servidor")
      }
    })
  }

  sortBy(sort: UserSort) {
    this.currentSort = sort;
    this.fetchContent()
  }

  goNextPage() {
    if (!this.pagination.last) {
      this.currentPage += 1
      this.fetchContent()
    }
  }

  goPreviousPage() {
    if (!this.pagination.first) {
      this.currentPage -= 1
      this.fetchContent()
    }
  }
}
