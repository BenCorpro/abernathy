export class Patient{
    id!: number;
    firstname!: string;
    lastname!: string;
    birthdate!: Date;
    gender!: 'MALE' | 'FEMALE';
    address!: string;
    phone!: string;
}