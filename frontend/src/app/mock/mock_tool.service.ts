import { Observable, Observer } from "rxjs";
import { Pageable } from "../interfaces/pageable";
import { Tool } from "../models/tool";
import { ToolService, ToolSort } from "../services/tool.service";
import { Injectable } from "@angular/core";

@Injectable()
export class MockToolService extends ToolService {

    static mockTools: Tool[] = [{
        id: 0,
        name: 'mock-tool',
        brand: {
            id: 0,
            name: 'mock-brand'
        },
        description: "This is a mock tool",
        price: 0,
        cities: [
            {
                id: 0,
                name: 'mock-city'
            }
        ],
        units: 1,
    }];

    override searchAllTools(sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
        return new Observable<Pageable<Tool>>((observer: Observer<Pageable<Tool>>) => {
            observer.next({
                content: MockToolService.mockTools,
                pageable: {
                    sort: {
                        empty: true,
                        unsorted: true,
                        sorted: false
                    },
                    offset: 0,
                    pageNumber: 0,
                    pageSize: 0,
                    paged: false,
                    unpaged: true,
                },
                totalPages: 0,
                totalElements: 0,
                last: false,
                size: 0,
                number: 0,
                sort: {
                    empty: true,
                    unsorted: true,
                    sorted: false,
                },
                numberOfElements: 0,
                first: false,
                empty: true,
            })
        })
    }


    override searchByName(name: string, sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
        return new Observable<Pageable<Tool>>((observer: Observer<Pageable<Tool>>) => {
            observer.next({
                content: [],
                pageable: {
                    sort: {
                        empty: true,
                        unsorted: true,
                        sorted: false
                    },
                    offset: 0,
                    pageNumber: 0,
                    pageSize: 0,
                    paged: false,
                    unpaged: true,
                },
                totalPages: 0,
                totalElements: 0,
                last: false,
                size: 0,
                number: 0,
                sort: {
                    empty: true,
                    unsorted: true,
                    sorted: false,
                },
                numberOfElements: 0,
                first: false,
                empty: true,
            })
        })
    }

    override searchByBrand(brand: string, sort: ToolSort = ToolSort.NONE, page: number = 0): Observable<Pageable<Tool>> {
        return new Observable<Pageable<Tool>>((observer: Observer<Pageable<Tool>>) => {
            observer.next({
                content: [],
                pageable: {
                    sort: {
                        empty: true,
                        unsorted: true,
                        sorted: false
                    },
                    offset: 0,
                    pageNumber: 0,
                    pageSize: 0,
                    paged: false,
                    unpaged: true,
                },
                totalPages: 0,
                totalElements: 0,
                last: false,
                size: 0,
                number: 0,
                sort: {
                    empty: true,
                    unsorted: true,
                    sorted: false,
                },
                numberOfElements: 0,
                first: false,
                empty: true,
            })
        })
    }
}
