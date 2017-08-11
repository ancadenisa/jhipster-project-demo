import { BaseEntity } from './../../shared';

export class Cherry implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: any,
        public date?: any,
    ) {
    }
}
