import {User} from "./user";

export class Relation {

    /**
     * @var string|null
     */
    key: any;
    sender: User;
    receiver: User;
    accepted: boolean;
    invitationDate: any;
    acceptedDate: any;
}
