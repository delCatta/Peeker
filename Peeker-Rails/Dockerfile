FROM ruby:3.1.2

WORKDIR /usr/src/app

RUN apt-get update -q \
    && apt-get install --assume-yes -q --no-install-recommends build-essential libpq-dev libvips \
    && apt-get autoremove --assume-yes \
    && rm -Rf /var/cache/apt \
    && rm -Rf /var/lib/apt/lists/*

ENV RAILS_ENV production
ENV RAILS_SERVE_STATIC_FILES true
ENV RAILS_LOG_TO_STDOUT true

COPY Gemfile /usr/src/app/
COPY Gemfile.lock /usr/src/app/
RUN bundle config --global frozen 1
RUN bundle config set --local without 'development test'
RUN bundle install

COPY . /usr/src/app

EXPOSE 3000

CMD ["bundle", "exec", "rails", "server", "-b", "0.0.0.0", "-p", "3000"]