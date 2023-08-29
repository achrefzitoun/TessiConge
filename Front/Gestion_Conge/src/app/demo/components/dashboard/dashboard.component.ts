import { Component, OnInit, OnDestroy } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Product } from '../../api/product';
import { ProductService } from '../../service/product.service';
import { Subscription } from 'rxjs';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
import { DashbordService } from 'src/app/Services/Dashbord/dashbord.service';
import { Autorisation } from 'src/app/Model/Autorisation';

@Component({
    templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit, OnDestroy {

    items!: MenuItem[];

    products!: Product[];

    chartData: any;

    chartOptions: any;

    subscription!: Subscription;

    constructor(private productService: ProductService, public layoutService: LayoutService, private dashbordServices: DashbordService) {
        this.subscription = this.layoutService.configUpdate$.subscribe(() => {
            this.initChart();
        });
    }

    autorisation: number;
    congeAujourdhui: number;
    congeCeMois: number;
    congeEnAttente: number;
    nbEmp: number;
    employeeDiaglog: boolean = false;
    autorisations: Autorisation[];
    cols: any[] = [];
    a: number;
    z: number;
    e: number;
    r: number;
    t: number;
    y: number;
    u: number;
    i: number;
    o: number;
    p: number;
    q: number;
    s: number;

    ngOnInit() {
        this.dashbordServices.getNbCongeMois(1).subscribe(a => this.a = a);
        this.dashbordServices.getNbCongeMois(2).subscribe(a => this.z = a);
        this.dashbordServices.getNbCongeMois(3).subscribe(a => this.e = a);
        this.dashbordServices.getNbCongeMois(4).subscribe(a => this.r = a);
        this.dashbordServices.getNbCongeMois(5).subscribe(a => this.t = a);
        this.dashbordServices.getNbCongeMois(6).subscribe(a => this.y = a);
        this.dashbordServices.getNbCongeMois(7).subscribe(a => this.u = a);
        this.dashbordServices.getNbCongeMois(8).subscribe(a => this.i = a);
        this.dashbordServices.getNbCongeMois(9).subscribe(a => this.o = a);
        this.dashbordServices.getNbCongeMois(10).subscribe(a => this.p = a);
        this.dashbordServices.getNbCongeMois(11).subscribe(a => this.q = a);
        this.dashbordServices.getNbCongeMois(12).subscribe(a => { this.s = a; this.initChart(); });



        this.dashbordServices.countEmp().subscribe(data => this.nbEmp = data);
        this.dashbordServices.nbAutorisation().subscribe(data => this.autorisation = data);
        this.dashbordServices.nbCongeMois().subscribe(data => this.congeCeMois = data);
        this.dashbordServices.nbEnAttente().subscribe(data => this.congeEnAttente = data);
        this.dashbordServices.nbCongeAujourdhui().subscribe(data => this.congeAujourdhui = data);

    }

    initChart() {
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

        this.chartData = {
            labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Aout', 'Septembre', 'Octobre', 'Novembre', 'Decembre'],
            datasets: [
                {
                    label: 'First Dataset',
                    data: [
                        this.a,
                        this.z,
                        this.e,
                        this.r,
                        this.t,
                        this.y,
                        this.u,
                        this.i,
                        this.o,
                        this.p,
                        this.q,
                        this.s,

                    ],
                    fill: false,
                    backgroundColor: documentStyle.getPropertyValue('--bluegray-700'),
                    borderColor: documentStyle.getPropertyValue('--bluegray-700'),
                    tension: .4
                }
            ]
        };

        this.chartOptions = {
            plugins: {
                legend: {
                    labels: {
                        color: textColor
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        color: textColorSecondary
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                },
                y: {
                    ticks: {
                        color: textColorSecondary
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                }
            }
        };
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    listAutorisation() {
        this.dashbordServices.getListAutorisation().subscribe(data => this.autorisations = data);
        this.employeeDiaglog = true;
    }
}
