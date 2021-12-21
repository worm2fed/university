FROM php:7.2-apache

RUN apt-get update
RUN apt-get install libpng-dev -y

RUN docker-php-ext-install mysqli && docker-php-ext-enable mysqli
RUN docker-php-ext-install gd && docker-php-ext-enable gd

RUN a2enmod rewrite
RUN service apache2 restart
