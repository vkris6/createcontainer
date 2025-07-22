FROM fedora

RUN dnf -y install \
    make \
    golang \
    bats \
    btrfs-progs-devel \
    device-mapper-devel \
    glib2-devel \
    gpgme-devel \
    libassuan-devel \
    libseccomp-devel \
    ostree-devel \
    git \
    bzip2 \
    go-md2man \
    runc \
    containers-common

RUN mkdir -p ~/buildah; \
  cd ~/buildah; \
  export GOPATH=`pwd`; \
  git clone https://github.com/containers/buildah ./src/github.com/containers/buildah; \
  cd ./src/github.com/containers/buildah; \
  make; \
  make install; \
  buildah --help
RUN dnf -y install podman
RUN dnf -y install groovy
RUN dnf -y install wget
RUN curl https://sdk.cloud.google.com | bash
RUN mkdir /root/.ssh/
COPY ./id_rsa /root/.ssh/id_rsa
COPY ./key.json /root/key.json
RUN touch /root/.ssh/known_hosts
RUN ssh-keyscan github.com >> /root/.ssh/known_hosts

COPY ./entrypoint.sh /root/entrypoint.sh
WORKDIR /root
ENTRYPOINT ["/bin/bash", "-c"]
