package com.chaatgadrive.arif.chaatgadrive.models;

/**
 * Created by Arif on 2/20/2018.
 */

public class DistanceModel {

        private double sourceLat=0;
        private double sourceLong=0;
        private double destinationLat=0;
        private double destinationLong=0;
        private double totaldistance=0;


        public double getSourceLat() {
            return sourceLat;
        }

        public void setSourceLat(double sourceLat) {
            this.sourceLat = sourceLat;
        }

        public double getSourceLong() {
            return sourceLong;
        }

        public void setSourceLong(double sourceLong) {
            this.sourceLong = sourceLong;
        }

        public double getDestinationLat() {
            return destinationLat;
        }

        public void setDestinationLat(double destinationLat) {
            this.destinationLat = destinationLat;
        }

        public double getDestinationLong() {
            return destinationLong;
        }

        public void setDestinationLong(double destinationLong) {
            this.destinationLong = destinationLong;
        }

        public double getTotaldistance() {
            return totaldistance;
        }

        public void setTotaldistance(double totaldistance) {
            this.totaldistance = totaldistance;
        }
    }
