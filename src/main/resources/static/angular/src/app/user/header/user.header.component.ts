import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {User} from "../user";
import {UserServiceInterface} from "../user.service.interface";
import {Relation} from "../relation";
import {Invitation} from "../invitation";
import {Swal} from "../../shared/swal";

@Component({
    selector:    'user-header',
    templateUrl: 'user.header.component.html'
})
export class UserHeaderComponent implements OnInit, OnChanges {

    @Input() user: User;
    currentUser: User;
    relation: Relation;
    invitationsCount: number;
    invitations: Invitation[];
    invitationsModal: any;

    constructor(
        @Inject('UserServiceInterface') private userService: UserServiceInterface
    ) {
        this.currentUser = new User();
        this.relation = new Relation();
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.user = changes.user.currentValue;
        this.getRelation();
        this.getInvitationsCount();
    }

    ngOnInit(): void {
        this.getCurrentUser();
        this.getRelation();
        this.getInvitationsCount();
    }

    public invite(user: User): void {
        this.userService.inviteUser(user).then(accepted => this.relation.accepted = accepted);
    }

    public deleteRelation(user: User): void {
        Swal.confirm(() => {
            this.userService.deleteRelationByUserId(user.id).then(accepted => {
                this.relation.accepted = accepted;
                Swal.success('Removed!', '<strong>' + user.username + '</strong> is not more your friend.');
            })
        });
    }

    public loadInvitations(): void {
        let hasNextPage: boolean = false;
        let page: number = 0;
        do {
            this.userService.getCurrentUserInvitations(page).then(response => {
                hasNextPage = response.hasNextPage();
                page = response.getNextPage();
                if (typeof this.invitations === "undefined") {
                    this.invitations = response.getResponse();
                } else {
                    response.getResponse().forEach(invitation => this.invitations.unshift(invitation));
                }
            });
        } while (hasNextPage);
    }

    public accept(invitation: Invitation): void {
        this.userService.acceptByUserId(invitation.id).then(() => {
            this.deleteInvitation(invitation);
            Swal.success('Accepted!', 'Invitation from <strong>' + invitation.username + '</strong> has been accepted.');
        });
    }

    public reject(invitation: Invitation): void {
        Swal.confirm(() => {
            this.userService.acceptByUserId(invitation.id).then(() => {
                this.deleteInvitation(invitation);
                Swal.success('Rejected!', 'Invitation from <strong>' + invitation.username + '</strong> has been rejected.');
            })
        });

    }

    private deleteInvitation(invitation: Invitation): void {
        let invitationIndex: number = this.invitations.indexOf(invitation);
        this.invitations.splice(invitationIndex, 1);
    }

    private getCurrentUser(): void {
        this.userService.getCurrentUser().then(user => this.currentUser = user);
    }

    private getRelation(): void {
        this.userService.getRelationByUser(this.user).then(relation => {
            this.relation = relation
        });
    }

    private getInvitationsCount(): void {
        this.userService.countCurrentUserInvitations().then(count => {
            this.invitationsCount = count
        });
    }
}
